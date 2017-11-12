app.controller('accountCourseController', function($scope, $stateParams, $http, $state){
    //查询列表
    $scope.state;
    $scope.effective;
    $scope.deadline;
    $scope.forceTime;
    $scope.accountId = $stateParams.accountId;
    $scope.states = [];
    //列表参数
    $scope.list = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.reset = function(){
        $scope.state         = '';
        $scope.effective     = '';
        $scope.deadline      = '';
        $scope.forceTime     = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.getData = function(){
        //查询参数
        var pageNum       = $scope.pagingOptions.currentPage,
            pageSize      = $scope.pagingOptions.pageSize,
            effective     = $scope.effective,
            state         = $scope.state,
            deadline      = $scope.deadline,
            forceTime     = $scope.forceTime,
            accountId     = $scope.accountId;

        $http({
            url: '/api/1.0/accountCourse/page',
            method: 'POST',
            data: $.param({
                'pageNum'       : pageNum,
                'pageSize'      : pageSize,
                'accountId'     : accountId,
                'state'         : state,
                'effective'     : effective,
                'deadline'      : deadline,
                'forceTime'     : forceTime
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
        });
    };

    $scope.delete = function(id){
        var result = confirm('确认删除该用户的课程 ?');

        if(result){
            $http({
                url: '/api/1.0/accountCourse/' + id,
                method: 'DELETE'
            }).success(function(res, status, headers, config){
                if(res.success){
                    alert(res.msg);
                    $scope.pagingOptions.currentPage = 1;
                    $scope.getData();
                }
            }).error(function(response){

            });
        }
    };

    $scope.getState = function(){
        $http({
            url: '/api/1.0/usingState/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.states = res.result;
            }
        }).error(function(response){
            $scope.states = [];
        });
    };

    $scope.edit = function(id){
        $state.go('postEdit', {'id' : id});
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
            field: 'id',
            visible: false
        },{
            field: 'course.title',
            displayName: '标题'
        },{
            field: 'forceTime',
            displayName: '生效时间',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            field: 'effective',
            displayName: '有效天数'
        },{
            field: 'deadline',
            displayName: '有效期',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            field: 'state',
            displayName: '状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{states[COL_FIELD]}}</span></div>'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a><a href="javascript:;" ng-click="delete(row.getProperty(\'id\'))">[删除]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getState();
});