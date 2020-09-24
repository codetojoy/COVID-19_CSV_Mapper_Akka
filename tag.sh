#!/bin/bash

TAG=simple.gold.v5
MESSAGE="reset simple.gold with build.gradle"

git tag -a $TAG -m "${MESSAGE}"
git push origin $TAG

