app.controller('simNumUserController', function($scope){
    $scope.list = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.getData = function(pageNum, pageSize){
        $.ajax({
            url: '/api/1.0/simNumUser/page',
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
            field: 'sim',
            displayName: 'sim卡号码'
        },{
            field: 'num',
            displayName: '本机号码'
        },{
            field: 'userName',
            displayName: '使用者名称'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="simNumUserEdit({sim:\'{{row.getProperty(\'sim\')}}\'})">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData($scope.pagingOptions.currentPage, $scope.pagingOptions.pageSize);
});