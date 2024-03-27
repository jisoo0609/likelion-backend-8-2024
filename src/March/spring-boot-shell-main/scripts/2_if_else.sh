#!/bin/bash


# Shell의 if는 명령어의 결과에 따라 실행 여부가 바뀐다.
if pwd; then
  echo "success: $?"
else
  echo "fail: $?"
fi

# 대괄호는 `test` 명령어의 약칭
# if test 2 -eq 2; then
if [[ 2 -eq 2 ]]; then
  echo "equal"
fi

# 숫자 비교
a=5
b=10

if [[ $a -lt $b ]]; then
  echo "$a is less than $b"
elif [[ $a -gt $b ]]; then
  echo "$a is greater than $b"
else
  echo "$a = $b"
fi

# 문자열 비교
str1="hello"
str2="hello"
if [ $str1 = $str2 ]; then
  echo "Strings are equal"
fi

# 정수 비교
test 2 -eq 3  # 일치 여부
test 2 -ne 3  # 불일치 여부
test 2 -gt 3  # Greater Than
test 2 -lt 3  # Less Than
test 2 -ge 3  # Greater than or Equal
test ! 2 -gt 3  # not

# 문자열 비교
test hello = world   # 일치 여부
test hello != world  # 불일치 여부
test -n "hello"      # 문자열이 빈 문자열이 아닌지
test -z "hello"      # 문자열이 빈 문자열이 맞는지

# 파일 확인
test -e img.png  # exists, 파일이 존재하는지
test -f img.png  # file, 폴더가 아닌 파일인지
test -d img.png  # directory, 폴더인지

