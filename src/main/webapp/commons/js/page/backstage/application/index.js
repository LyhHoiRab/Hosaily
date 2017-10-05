app.controller('applicationController', function($scope){
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
    	0: '网站应用',
    	1: '小程序',
    	2: '微信公众帐号'
    };

    $scope.getData = function(pageNum, pageSize){
        $.ajax({
            url: '/api/1.0/application/page',
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
            field: 'name',
            displayName: '名称',
            minWidth: 150
        },{
        	field: 'appId',
        	displayName: 'APPID',
        	minWidth: 150
        },{
        	field: 'token',
        	displayName: 'TOKEN',
        	minWidth: 150
        },{
        	field: 'type',
        	displayName: '类型',
        	minWidth: 150,
        	cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{type[COL_FIELD]}}</span></div>'
        },{
            field: 'state',
            displayName: '状态',
            minWidth: 150,
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{state[COL_FIELD]}}</span></div>'
        },{
        	field: 'createTime',
        	displayName: '创建时间',
            minWidth: 150,
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
        	field: 'updateTime',
        	displayName: '更新时间',
            minWidth: 150,
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            displayName: '操作',
            minWidth: 150,
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="applicationEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData($scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
});