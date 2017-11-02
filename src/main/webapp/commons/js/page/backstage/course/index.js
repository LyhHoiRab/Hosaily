app.controller('courseController', function($scope, $http){
    ////枚举常量
    //$scope.state = {
    //    0: '正常',
    //    1: '未激活',
    //    2: '锁定',
    //    3: '冻结',
    //    4: '不可用'
    //};

    $scope.states = [];
    $scope.state;
    $scope.advisor;
    $scope.tag;
    $scope.createTime;
    $scope.minCreateTime;
    $scope.maxCreateTime;
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
        $scope.tag           = '';
        $scope.advisor       = '';
        $scope.createTime    = '';
        $scope.minCreateTime = '';
        $scope.maxCreateTime = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.pagingOptions.currentPage = $scope.pagingOptions.currentPage;
        $scope.getData();
    };

    $scope.getData = function(){
        var pageNum       = $scope.pagingOptions.currentPage, 
            pageSize      = $scope.pagingOptions.pageSize,
            advisor       = $scope.advisor,
            tag           = $scope.tag,
            state         = $scope.state,
            createTime    = $scope.createTime,
            minCreateTime = $scope.minCreateTime,
            maxCreateTime = $scope.maxCreateTime;

        $http({
            url: '/api/1.0/course/page',
            method: 'POST',
            data: $.param({
                'pageNum'       : pageNum,
                'pageSize'      : pageSize,
                'advisor'       : advisor,
                'tag'           : tag,
                'state'         : state,
                'createTime'    : createTime,
                'minCreateTime' : minCreateTime,
                'maxCreateTime' : maxCreateTime
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
        });;

        //$.ajax({
        //    url: '/api/1.0/course/page',
        //    type: 'POST',
        //    dataType: 'JSON',
        //    data: {
        //        'pageNum'       : pageNum,
        //        'pageSize'      : pageSize,
        //        'advisor'       : advisor,
        //        'tag'           : tag,
        //        'state'         : state,
        //        'createTime'    : createTime,
        //        'minCreateTime' : minCreateTime,
        //        'maxCreateTime' : maxCreateTime
        //
        //    },
        //    success: function(res){
        //        if(res.success){
        //            $scope.list = res.result.content;
        //            $scope.total = res.result.total;
        //        }
        //        if(!$scope.$$phase){
        //            $scope.$apply();
        //        }
        //    }
        //});
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

    //初始化数据
    $scope.getData();
    $scope.getState();

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
            field: 'comments',
            displayName: '评论量'
        },{
            field: 'sort',
            displayName: '排序'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="courseEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a></span></div>'
        }]
    };
});