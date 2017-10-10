app.controller('mediaController', function($scope){
	$scope.list = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    //枚举常量
    $scope.state = {
        0: '正常',
        1: '未激活',
        2: '锁定',
        3: '冻结',
        4: '不可用'
    };
    $scope.type = {
    	0: '未知',
    	1: '音频',
    	2: '视频',
    	3: '图片',
    	4: '文本'
    };

    $scope.getData = function(pageNum, pageSize){
        $.ajax({
            url: '/api/1.0/media/page',
            type: 'POST',
            dataType: 'JSON',
            data: {
                'pageNum': pageNum,
                'pageSize': pageSize
            },
            success: function(res){
                if(res.success){
                    $scope.list = res.result.content;
                    $scope.total = res.result.total;
                }
                if(!$scope.$$phase){
                    $scope.$apply();
                }
            }
        });
    };

    $scope.$watch('pagingOptions', function(newVal, oldVal){
        if(newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize)){
            var pageNum = newVal.currentPage;
            var pageSize = newVal.pageSize;

            $scope.getData(pageNum, pageSize);
        }
    }, true);

    $scope.gridOptions = {
        data                   : 'list',
        enablePaging           : true,
        enableSorting          : false,
        enableRowSelection     : false,
        showFooter             : true,
        showSelectionCheckbox  : false,
        selectWithCheckboxOnly : true,
        multiSelect            : false,
        keepLastSelected       : false,
        selectItems            : $scope.selected,
        pagingOptions          : $scope.pagingOptions,
        totalServerItems       : 'total',
        i18n                   : 'zh-cn',
        columnDefs: [{
            field: 'id',
            visible: false
        },{
            field: 'fileName',
            displayName: '文件名称'
        },{
            field: 'suffix',
            displayName: '格式'
        },{
            field: 'type',
            displayName: '类型',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{type[COL_FIELD]}}</span></div>'
        },{
            field: 'url',
            displayName: '外链'
        },{
            field: 'md5',
            displayName: 'MD5'
        },{
            field: 'size',
            displayName: '大小(MB)',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD/1024/1024 | number:3}}</span></div>'
        },{
            field: 'remark',
            displayName: '备注'
        },{
            field: 'state',
            displayName: '状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{state[COL_FIELD]}}</span></div>'
        },{
            field: 'createTime',
            displayName: '创建时间',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            field: 'updateTime',
            displayName: '更新时间',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            displayName: '操作',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="mediaEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData($scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
});