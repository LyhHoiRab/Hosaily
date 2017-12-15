app.controller('simNumUserListController', function($scope, $http, $stateParams){
    $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.userType;
    $scope.organizationId  = $stateParams.organizationId;
    $scope.list = [];
    $scope.organizations = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.reset = function(){
        $scope.num           = '';
        $scope.userType = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.edit = function(id){
        $state.go('simNumUserEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('simNumUserAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.getData = function(){
        //查询参数
        var pageNum       = $scope.pagingOptions.currentPage,
            pageSize      = $scope.pagingOptions.pageSize,
            num           = $scope.num;
        userType     = $scope.userType;
        $http({
            url: '/api/1.0/simNumUser/page',
            method: 'POST',
            data: $.param({
                'pageNum': pageNum,
                'pageSize': pageSize,
                'num':num,
                'userType':userType,
                'organizationId' : $scope.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.list = res.result.content;
                $scope.total = res.result.total;
            }
        }).error(function(response){
            $scope.list = [];
            $scope.total = 0;
            console.error(response);
        });
    };

    $scope.delete = function(id){
        $http({
            url: '/api/1.0/simNumUser/' + id,
            method: 'DELETE'
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $scope.pagingOptions.currentPage = 1;
                $scope.getData();
            }else{
                alert(res.msg);
            }
        }).error(function(response){

        });
    };

    $scope.$watch('pagingOptions', function(newVal, oldVal){
        if(newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize)){
            $scope.getData();
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
            field: 'userType',
            displayName: '使用者类型'
        },{
            field: 'organizationId',
            displayName: '平台类型'
        },{
            //     field: 'createTime',
            //     displayName: '创建时间',
            //     cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
            // },{
            //     field: 'updateTime',
            //     displayName: '更新时间',
            //     cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
            // },{
            displayName: '操作',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="simNumUserEdit({sim:\'{{row.getProperty(\'sim\')}}\'})">[修改]</a><a href="javascript:;" ng-click="delete(row.getProperty(\'sim\'))">[删除]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
});