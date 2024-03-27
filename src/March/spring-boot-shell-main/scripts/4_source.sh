#!/bin/bash

# export는 실행된 Shell Script 내부에서만 유효하지만
# source 명령을 사용하면 해당 스크립트를 자신의 Shell Session에서 실행한다.
export TEST_ENV_VAR="testing source"
