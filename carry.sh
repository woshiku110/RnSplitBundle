#!/usr/bin/env bash
path='/Users/woshiku/Desktop/github/RnSplitBundle'
pathResource='/Users/woshiku/Desktop/github/RnSplitBundle/android/app/src/main/assets/'
cd ${path}
pwd
yarn run common
echo "通包生成完成，业务包生成中..."
yarn run moudlea
yarn run moudleb
echo "业务包生成完成。运行对比服务..."
cd "${path}/mybundle"
pwd
echo "通包生成完成，业务包生成完成。对比服务完成，生成差异包..."
node ./index.js common.bundle moudlea.bundle adiff.bundle
node ./index.js common.bundle moudleb.bundle bdiff.bundle
rm -rf "${pathResource}common.bundle"
rm -rf "${pathResource}adiff.bundle"
rm -rf "${pathResource}bdiff.bundle"
mv common.bundle ${pathResource}
mv adiff.bundle ${pathResource}
mv bdiff.bundle ${pathResource}
echo "通包生成完成，业务包生成完成。对比服务完成，差异包完成,目录移动指定位置。"
#cd /Users/woshiku/Desktop/gkt/git/gktapp/android
#./gradlew assembleDebug
#cd /Users/woshiku
#adb uninstall com.gkt
#adb install /Users/woshiku/Desktop/gkt/git/gktapp/android/app/build/outputs/apk/dev/debug/app-dev-debug.apk
#adb shell am start -n com.gkt/com.gktapp.MainActivity
#cd /Users/woshiku/Desktop/gkt/git/gktapp/
#react-native run-android  --variant=devDebug
