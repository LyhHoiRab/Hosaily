app.controller('wechatListController', function($scope, $http, $state, $stateParams){
    //查询列表
    $scope.state           = '';
    $scope.organizationId  = $stateParams.organizationId;
    $scope.seller          = '';
    $scope.nickname        = '';
    $scope.advisor         = '';

    //下拉
    $scope.advisors      = [];
    $scope.states        = {};
    $scope.organizations = [];

    //列表参数
    $scope.list = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [10, 50, 100, 200],
        pageSize: 10,
        currentPage: 1
    };

    $scope.reset = function(){
        $scope.state          = '';
        $scope.seller         = '';
        $scope.nickname       = '';
        $scope.advisor        = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.edit = function(id){
        $state.go('wechatEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('wechatAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.getData = function(){
        $http({
            url: '/api/1.0/popularize/wechat/page',
            method: 'GET',
            params: {
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'nickname'       : $scope.nickname,
                'seller'         : $scope.seller,
                'state'          : $scope.state,
                'organizationId' : $scope.organizationId,
                'advisorId'      : $scope.advisor
            },
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

    $scope.getAdvisor = function(){
        $http({
            url: '/api/1.0/advisor/list',
            method: 'POST',
            data: $.param({
                'organizationId' : $scope.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.advisors = res.result;
            }
        }).error(function(response){
            $scope.advisors = [];
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
        rowHeight              : 60,
        columnDefs: [{
            field: 'id',
            visible: false
        },{
            field: 'wxno',
            displayName: '微信'
        },{
            field: 'nickname',
            displayName: '昵称'
        },{
            field: 'headImgUrl',
            displayName: '头像',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><img src="{{COL_FIELD}}" width="50" height="50" style="vertical-align:middle;"></div>'
        },{
            field: 'advisor.name',
            displayName: '关联导师'
        },{
            field: 'remark',
            displayName: '备注'
        },{
            field: 'seller',
            displayName: '销售'
        },{
            field: 'state',
            displayName: '状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{states[COL_FIELD]}}</span></div>'
        },{
            displayName: '操作',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getState();
    $scope.getAdvisor();
});