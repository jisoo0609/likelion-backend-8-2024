#!/bin/bash

# 빌드를 진행한다.
./gradlew bootJar
# jar 파일을 찾는다.
if [ -f build/libs/*.jar ]; then
  echo "found jar"
  # 마지막 실행된 jar를 찾아본다.
  last_pid=$(pgrep -f app.jar)
  # 있다면 해당 프로세스 종료
  if [ ! -z $last_pid ]; then
    kill -15 $last_pid
  fi

  # 처음 살행해본 경우 `run` 경로 생성
  if [ ! -d run/ ]; then
    mkdir "run"
  fi

  # 이전 파일이 존재했을 경우 백업 생성
  if [ -f run/app.jar ]; then
    mv "run/app.jar" "run/app-$(date '+%Y-%m-%d-%H:%M:%S').jar"
  fi

  # 만들어진 jar를 실행 경로로 이동하고
  mv build/libs/*.jar run/app.jar
  # 백그라운드에서 실행한다.
  nohup java -jar run/app.jar > run/access.log 2> run/error.log &
else
  echo "failed to find jar" >&2
  exit 1
fi
