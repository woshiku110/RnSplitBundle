/**
 * 生成模块Id
 * Create by woshiku
 */
'use strict';

function createModuleIdFactory() {
    // 定义项目根目录路径，此处以 Windows 平台为例
    const projectRootPath = '/Users/woshiku/Desktop/github/RnSplitBundle';
    // path 为模块路径名称
    return path => {
        let moduleName = '';
        if(path.indexOf('node_modules\\react-native\\Libraries\\') > 0) {
            moduleName = path.substr(path.lastIndexOf('\\') + 1);
        } else if(path.indexOf(projectRootPath)==0){
            moduleName = path.substr(projectRootPath.length + 1);
        }
        // 可按照自己的方式优化路径名称显示
        moduleName = moduleName.replace('.js', '');
        moduleName = moduleName.replace('.png', '');
        moduleName = moduleName.replace('/\\/g', '_'); // 适配Windows平台路径问题

        return moduleName;
    };
}

module.exports = {
    /* serializer options */
    serializer: {
        createModuleIdFactory: createModuleIdFactory
    }
};
