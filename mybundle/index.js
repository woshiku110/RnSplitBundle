var fs = require('fs');
var readline = require('readline');

function readFileToArr(fReadName,callback){
    var fRead = fs.createReadStream(fReadName);
    var objReadline = readline.createInterface({
        input:fRead
    });
    var arr = new Array();
    objReadline.on('line',function (line) {
        arr.push(line);
        //console.log('line:'+ line);
    });
    objReadline.on('close',function () {
        // console.log(arr);
        callback(arr);
    });
}

var argvs = process.argv.splice(2);
var commonFile = argvs[0]; // common.ios.jsbundle
var businessFile = argvs[1]; // business.ios.jsbundle
var diffOut = argvs[2]; // diff.ios.jsbundle
readFileToArr(commonFile, function (c_data) {
    var diff = [];
    var commonArrs = c_data;
    readFileToArr(businessFile, function (b_data) {
        var businessArrs = b_data;
        for (let i = 0; i < businessArrs.length; i++) {
            if (commonArrs.indexOf(businessArrs[i]) === -1) { // business中独有的行
                diff.push(businessArrs[i]);
            }
        }
        // console.log(diff.length);
        var newContent = diff.join('\n');
        fs.writeFileSync(diffOut, newContent); // 生成diff.ios.jsbundle
    });
});
