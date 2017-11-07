app.controller('salesController', function($scope, $http){
    //查询列表
    $scope.state;
    $scope.name;
    $scope.nickname;
    $scope.code;
    $scope.unionId;
    $scope.createTime;
    $scope.states = [];
    $scope.sexs   = [];
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
        $scope.name          = '';
        $scope.nickname      = '';
        $scope.code          = '';
        $scope.createTime    = '';
        $scope.unionId       = '';
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
            name          = $scope.name,
            state         = $scope.state,
            createTime    = $scope.createTime,
            nickname      = $scope.nickname,
            code          = $scope.code,
            unionId       = $scope.unionId;

        $http({
            url: '/api/1.0/user/page',
            method: 'POST',
            data: $.param({
                'pageNum'       : pageNum,
                'pageSize'      : pageSize,
                'name'          : name,
                'state'         : state,
                'createTime'    : createTime,
                'code'          : code,
                'nickname'      : nickname,
                'wechat'        : unionId
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

    $scope.getSex = function(){
        $http({
            url: '/api/1.0/sex/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.sexs = res.result;
            }
        }).error(function(response){
            $scope.sexs = [];
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
            field: 'headImgUrl',
            displayName: '头像',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><img src="{{COL_FIELD}}" width="50" height="50" style="vertical-align:middle;"></div>'
        },{
            field: 'nickname',
            displayName: '昵称'
        },{
            field: 'name',
            displayName: '姓名'
        },{
            field: 'code',
            displayName: '编码'
        },{
            field: 'sex',
            displayName: '性别',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{sexs[COL_FIELD]}}</span></div>'
        },{
            field: 'state',
            displayName: '状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{states[COL_FIELD]}}</span></div>'
        },{
            field: 'createTime',
            displayName: '创建时间',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            displayName: '操作',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="salesEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getState();
    $scope.getSex();
});