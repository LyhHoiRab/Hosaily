app.controller('paymentTypeController', function($scope, $http){
    //查询列表
    $scope.state;
    $scope.name;
    $scope.createTime;
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
        $scope.name          = '';
        $scope.createTime    = '';
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
            createTime    = $scope.createTime;

        $http({
            url: '/api/1.0/paymentType/page',
            method: 'POST',
            data: $.param({
                'pageNum'       : pageNum,
                'pageSize'      : pageSize,
                'name'          : name,
                'state'         : state,
                'createTime'    : createTime
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
            field: 'imgUrl',
            displayName: '图片',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><img src="{{COL_FIELD}}" width="50" height="50" style="vertical-align:middle;"></div>'
        },{
            field: 'name',
            displayName: '名称'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="paymentTypeEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getState();
});