```shell
## 恢复到修改前
git reset --hard HEAD^
### 恢复到指定版本
git log ## 查看版本号
git reset --hard [版本号]
git push --force  这里就很简单了，强制把本地重置好的推给远程，此时的origin/test-release就和本地保持一致了。

## clone远程分支到本地
git fetch origin [远程分支]:[本地分支名]
## 本地分支上传到远程仓库
git push origin [本地分支名]

### 删除本地分支
git branch -D [分支名]
##  删除远程分支
git push origin --delete [branch name]
```

