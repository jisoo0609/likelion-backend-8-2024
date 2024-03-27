#!/bin/bash

# Shell Script는 문자열을 기본으로 한다.
# 그리고 상황에 따라 정수 사칙연산이 가능하다.
name="Jeeho Park"
first_name=Jeeho
last_name=Park
grade=10

# 출력은 echo 명령으로
echo no_space_input
echo "Hello Shell Script!!"

# 변수를 사용하고 싶으면 '$' 사용
echo $name
echo "\"Hello, $name!!\""
echo 'Hello, $name!!!'

## 입력 받을 때는 read
#echo -n "Enter your name: "
#read name
#echo "Hello, $name!!!!"
## -p: Prompt
#read -p "Enter your name(prompt): " name
#echo "Hello, $name!!!"


# 실행당시의 환경변수도 사용 가능하다.
echo $HOME
echo $PWD
echo $PATH
## export도 사용가능하지만, 스크립트가 종료되면 변경이 사라진다.
#export PATH="nowhere"
#echo $PATH

# 문자열 내부에서 명령을 실행하고 싶다면, $()
# 현재 실행된 경로를 PATH에 추가
export PATH="$PATH:$(pwd)"
echo $PATH

# 사칙연산을 할 경우 $(( ... ))
a=2
b=3

echo "a + b: $(($a + $b))"
echo "a - b: $(($a - $b))"
echo "a * b: $(($a * $b))"
echo "a / b: $(($a / $b))"
echo "a % b: $(($a % $b))"


# 특수 변수: 스크립트 이름, 인자들, PID
echo "스크립트 이름: $0"
echo "스크립트 인자 갯수: $#"
echo "첫번째 인자: $1"
echo "두번째 인자: $2"
rmdir a
echo "마지막 명령의 결과(Exit Code): $?"


# 변수 주변에 문자를 넣고싶다면 ${}
echo "$gradeth"
echo "${grade}th"
