#!/bin/bash

# pgrep으로 PID를 찾는다.
current_pid=$(pgrep -f "app.jar")

# pgrep 명령의 exit code가 0인지 확인한다.
if [[ $? -eq 0 ]]; then
  # 종료할 PID를 출력하고 종료
  echo $current_pid
  kill -15 $current_pid
else
  echo "not found" >&2
  exit 1
fi
