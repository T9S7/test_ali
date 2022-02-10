#!/bin/sh
#chkconfig: 2345 99 10
#description: Startup and shutdown script for server.jar

export JAVA_HOME=/usr/lib/jvm/jre-1.8.0-openjdk-1.8.0.252.b09-2.el7_8.x86_64/
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH

WORK_DIR=/usr/local/abas/abas-backend-web
SERVER=$WORK_DIR/abas-backend-web-server.jar
ARGS="-Xms2g -Xmx2g -Xmn2g -Xss128k -XX:MaxPermSize=64m -XX:-UseParallelGC -XX:+UseParallelOldGC -XX:ParallelGCThreads=4 -XX:+UseConcMarkSweepGC -XX:MaxTenuringThreshol
d=30 -XX:SurvivorRatio=6 S"
cd $WORK_DIR

start()
{
        if test -e $SERVER
        then
                pid=`ps -ef | grep $SERVER | grep -v grep | awk '{print $2}'`
                if [ "$pid" ]
                then
                    echo "$SERVER is already running. pid=$pid ."
                else
                    echo -e "Starting $SERVER"
                    if java -jar -Dloader.path=$WORK_DIR/lib -Dfile.encoding=UTF-8 $SERVER $ARGS >/dev/null 2>&1 &
                    then
                        echo -e "server start OK"
                    else
                        echo -e "server start failed"
                    fi
                fi

        else
                echo -e "Couldn't find  $SERVER"
        fi
}

stop()
{
         pid=`ps -ef | grep $SERVER | grep -v grep | awk '{print $2}'`
         if [ -z "$pid" ]
         then
            echo "$SERVER is not runing"
            exit 0;
         fi

         echo -e "Stopping server $pid ..."
         if kill $pid
         then
             echo -e "server stop $SERVER OK"
         else
             echo -e "server stop $SERVER failed"
         fi


}

restart()
{
    echo -e "Restarting server..."
    stop
    start
}

status()
{
    pid=`ps -ef | grep $SERVER | grep -v grep | awk '{print $2}'`
    if [ -z "$pid" ]
    then
       echo "$SERVER is not running ..."
    else
       echo "$SERVER is running, pid $pid ..."
    fi
}

keepalive()
{
    pid=`ps -ef | grep $SERVER | grep -v grep | awk '{print $2}'`
    if [ -z "$pid" ]
    then
       echo "$SERVER is not running ..."
       start
    else
       echo "$SERVER is running, pid $pid ..."
    fi
}

case $1 in
         start)
                start
        ;;
         stop)
                stop
        ;;
         restart)
                restart
        ;;
         status)
               status
        ;;
         keepalive)
               keepalive
        ;;
         *)
                echo "Usage: $SCRIPTNAME {start|stop|restart|status}" >&2
        exit 1
        ;;
esac
exit 0