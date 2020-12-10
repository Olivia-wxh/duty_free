package com.shangchao.controller;

import com.alibaba.fastjson.JSONObject;
import com.shangchao.entity.Product;
import com.shangchao.entity.dto.ScProductQueryDto;
import com.shangchao.service.ProductService;
import com.shangchao.utils.ResponseUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/product")
@Api(tags = "产品管理相关接口", description = "如有疑问请联系王晓辉")
public class ProductController {

    @Autowired
    private ProductService productService;

    //    /**
    //     * 查询所有产品信息
    //     * @return
    //     */
    //    @GetMapping("/all")
    //    public JSONObject getAll(){
    //        JSONObject jo = new JSONObject();
    //        List<Product> all = productRepository.findAll();
    //        jo.put("list", all);
    //        return jo;
    //    }

    /**
     * 根据ID查询单品信息
     *
     * @return
     */
    @GetMapping("/id")
    @ApiOperation("根据商品ID获取商品信息")
    @ApiImplicitParam(name = "productId", value = "商品id", required = true)
    public JSONObject getById(String productId) {
        Product product = productService.getById(productId);
        return ResponseUtil.success(product);
    }

    /**
     * 查询产品列表（分页）
     *
     * @return
     */
    @GetMapping("/queryProductPageList")
    public JSONObject queryProductPageList(ScProductQueryDto queryDto) {
        JSONObject jo = new JSONObject();
        List<Product> all = productService.queryProductPageList(queryDto);
        jo.put("list", all);
        return jo;
    }

    /**
     * 根据专题ID获取商品集信息
     *
     * @return
     */
      @GetMapping("/topicId")
      @ApiOperation("根据专题ID获取商品集信息")
      @ApiImplicitParam(name = "topicId", value = "专题id", required = true)
      public JSONObject getByTopic(@RequestParam Integer currentPage, String topicId) {
          List<Product> product = productService.getByTopic(topicId, currentPage);
          return ResponseUtil.success(product);
      }

      /**
     * 获取所有品牌
     *
     * @return
     */
      @GetMapping("/getbrands")
      @ApiOperation("获取所有品牌")
      public JSONObject getBrands() {
          List<String> brands = productService.getBrands();
          return ResponseUtil.success(brands);
      }

    /**
     * 按品牌名称模糊查询
     *
     * @return
     */
    @GetMapping("/brandName")
    @ApiOperation("按品牌名称模糊查询")
    @ApiImplicitParam(name = "brandName", value = "品牌名称", required = true)
    public JSONObject getBrandsWithName(String brandName) {
        addHotWord(brandName);
        List<String> brands = productService.getBrandsWithName(brandName);
        return ResponseUtil.success(brands);
    }

    /**热搜
     *
     * 先引入RedisTemplate
     * StringRedisTemplate继承RedisTemplate，但是两者的数据是不共通的；
     * 也就是说StringRedisTemplate只能管理StringRedisTemplate里面的数据，
     * RedisTemplate只能管理RedisTemplate中的数据。
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置缓存失效时间，统一为凌晨零点
     * @param hotWord
     * @throws Exception
     */
    public void addHotWord(String hotWord) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR,1);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);
        //晚上十二点与当前时间的毫秒差
//        Long timeOut = (calendar.getTimeInMillis()-System.currentTimeMillis()) / 1000;
//        redisTemplate.expire("hotWord",timeOut, TimeUnit.SECONDS);
        redisTemplate.opsForZSet().incrementScore("hotWord", hotWord, 1); // 加入排序set
    }

    /**
     * 获取热词前10位
     * @return
     */
    @GetMapping("/getHotWord")
    @ApiOperation("获取热词前10位")
    public JSONObject getHotWord() {
        List<String> hotWordList = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<Object>> typedTupleSet = redisTemplate.opsForZSet().reverseRangeByScoreWithScores("hotWord",1,100);
        Iterator<ZSetOperations.TypedTuple<Object>> iterator = typedTupleSet.iterator();
        int flag = 0;
        while (iterator.hasNext()){
            flag++;
            ZSetOperations.TypedTuple<Object> typedTuple = iterator.next();
            String hotWord = (String)typedTuple.getValue();
//            int score = (int) Math.ceil(typedTuple.getScore());
//            HotWord hotWord = new HotWord(score,value);
            hotWordList.add(hotWord);
            if ( flag >= 10 ) break;
        }
        return ResponseUtil.success(hotWordList);
    }





}
