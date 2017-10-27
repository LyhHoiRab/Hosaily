app.controller('postController', function($scope){
    //枚举常量
    $scope.state = {
        0: '正常',
        1: '未激活',
        2: '锁定',
        3: '冻结',
        4: '不可用'
    };

    $scope.list = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.reset = function(){
        $('#advisor').val('');
        $('#state').val('');
        $('#createTime').val('');
        $('#minCreateTime').val('');
        $('#maxCreateTime').val('');
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
        //查询参数
        var pageNum       = $scope.pagingOptions.currentPage, 
            pageSize      = $scope.pagingOptions.pageSize,
            advisor       = $('#advisor').val(), 
            state         = $('#state').val(), 
            createTime    = $('#createTime').val(), 
            minCreateTime = $('#minCreateTime').val(), 
            maxCreateTime = $('#maxCreateTime').val();

        $.ajax({
            url: '/api/1.0/post/page',
            type: 'POST',
            dataType: 'JSON',
            data: {
                'pageNum'       : pageNum,
                'pageSize'      : pageSize,
                'advisor'       : advisor,
                'state'         : state,
                'createTime'    : createTime,
                'minCreateTime' : minCreateTime,
                'maxCreateTime' : maxCreateTime
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="postEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
});