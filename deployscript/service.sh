#!/bin/sh

rs=$1
JAR_NAME=$(
  cd ./ $(dirname $0)
  pwd
)/target/$1.jar
echo $JAR_NAME

pid=$(ps -ef | grep $rs.jar | grep -v grep | awk '{print $2}')

#使用说明，用来提示输入参数
usage() {
  echo "Usage: sh 执行脚本.sh [start|stop|restart|status]"
  exit 1
}

#检查程序是否在运行
is_exist() {
  #如果不存在返回1，存在返回0
  echo ">>> pid is $pid <<<"
  if [ ! $pid ]; then
    echo ">>> not is_exist <<<"
    return 1
  else
    echo ">>> is_exist <<<<"
    return 0
  fi
}

#启动方法
start() {
  nohup java -Xms256m -Xmx512m -jar $JAR_NAME &
  echo ">>> start $JAR_NAME successed PID=$! <<<"
  #tail -f nohup.out
}

#停止方法
stop() {
  is_exist
  is=$?
  if [ $is -eq 0 ]; then
    echo ">>> api 2 PID = $pid begin kill -9 $pid  <<<"
    kill -9 $pid
    sleep 2
    echo ">>> $JAR_NAME process stopped <<<"
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi
}

#输出运行状态
status() {
  is_exist
  if [ $? -eq "0" ]; then
    echo ">>> ${JAR_NAME} is running PID is ${pid} <<<"
  else
    echo ">>> ${JAR_NAME} is not running <<<"
  fi
}

#重启
restart() {
  stop
  start
}

#根据输入参数，选择执行对应方法，不输入则执行使用说明
case "$2" in
"start")
  start
  ;;
"stop")
  stop
  ;;
"status")
  status
  ;;
"restart")
  restart
  ;;
*)
  usage
  ;;
esac
exit 0
