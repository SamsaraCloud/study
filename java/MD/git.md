```shell
## 恢复到修改前
git reset --hard HEAD^
### 恢复到指定版本
git log ## 查看版本号
git reset --hard [版本号]

## clone远程分支到本地
git fetch origin [远程分支]:[本地分支名]
## 本地分支上传到远程仓库
git push origin [本地分支名]

### 删除本地分支
git branch -D [分支名]
##  删除远程分支
git push origin --delete [branch name]
```

