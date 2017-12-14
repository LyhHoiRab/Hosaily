app.controller('courseListController', function($scope, $http, $state, $stateParams){
    //下拉
    $scope.states         = {};
    $scope.organizations  = [];
    $scope.authorizations = {'false':'否', 'true':'是'};
    //查询列表
    $scope.state          = '';
    $scope.advisor        = '';
    $scope.tag            = '';
    $scope.organizationId = $stateParams.organizationId;
    $scope.list = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.reset = function(){
        $scope.state          = '';
        $scope.tag            = '';
        $scope.advisor        = '';
        //$scope.organizationId = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.getData = function(){
        $http({
            url: '/api/1.0/course/page',
            method: 'POST',
            data: $.param({
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'advisor'        : $scope.advisor,
                'state'          : $scope.state,
                'organizationId' : $scope.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.list = res.result.content;
                $scope.total = res.result.total;
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            $scope.list = [];
            $scope.total = 0;

            console.error(response);
        });
    };

    //$scope.getOrganization = function(){
    //    $http({
    //        url: '/api/1.0/organization/list',
    //        method: 'POST',
    //        data: $.param({
    //            'state' : 0
    //        }),
    //        headers: {
    //            'Content-Type': 'application/x-www-form-urlencoded'
    //        }
    //    }).success(function(res, status, headers, config){
    //        if(res.success){
    //            $scope.organizations = res.result;
    //        }
    //    }).error(function(response){
    //        $scope.organizations = [];
    //    });
    //};

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
        $state.go('courseEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('courseAdd', {'organizationId' : $scope.organizationId});
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
            field: 'title',
            displayName: '标题'
        },{
            field: 'summary',
            displayName: '概要'
        },{
            field: 'view',
            displayName: '浏览量'
        },{
            field: 'likes',
            displayName: '点赞量'
        },{
            field: 'sort',
            displayName: '排序'
        },{
            field: 'authorization',
            displayName: '是否需要授权',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{authorizations[COL_FIELD]}}</span></div>'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getState();
    //$scope.getOrganization();
});