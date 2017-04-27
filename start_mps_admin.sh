#!/bin/sh
. /etc/profile
export LANG=zh_CN.UTF-8

pid=`ps aux | grep 'mps-admin' | grep -v grep | awk '{print $2}'`
if [ "${pid}" != "" ]
then
  `kill -9 ${pid}`
fi

java -jar ./mps/mps-admin/target/mps-admin.jar &

  