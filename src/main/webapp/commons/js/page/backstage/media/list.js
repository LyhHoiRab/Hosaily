app.controller('mediaListController', function($scope, $state, $http, $stateParams){
    //查询条件
    $scope.state           = '';
    $scope.suffixType      = '';
    $scope.mediaType       = '';
    $scope.organization    = '';
    $scope.remark          = '';
    $scope.organizationId  = $stateParams.organizationId;
    //下拉
    $scope.states        = {};
    $scope.suffixTypes   = {};
    $scope.mediaTypes    = {};
    $scope.organizations = [];
    //列表
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
        $scope.name           = '';
        $scope.nickname       = '';
        //$scope.organizationId = '';
        $scope.remark         = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.edit = function(id){
        $state.go('mediaEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('mediaAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.getData = function(){
        $http({
            url: '/api/1.0/media/page',
            method: 'POST',
            data: $.param({
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'suffix'         : $scope.suffixType,
                'type'           : $scope.mediaType,
                'state'          : $scope.state,
                'organizationId' : $scope.organizationId,
                'remark'         : $scope.remark
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

    $scope.getState = function(){
        $http({
            url: '/api/1.0/usingState/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.states = res.result;
            }
        }).error(function(response){
            $scope.states = {};
        });
    };

    $scope.getOrganization = function(){
        $http({
            url: '/api/1.0/organization/list',
            method: 'POST',
            data: $.param({
                'state' : 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.organizations = res.result;
            }
        }).error(function(response){
            $scope.organizations = [];
        });
    };

    $scope.getSuffixType = function(){
        $http({
            url: '/api/1.0/suffixType/list',
            method: 'GET',
            data: $.param({
                'state' : 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.suffixTypes = res.result;
            }
        }).error(function(response){
            $scope.suffixTypes = {};
        });
    };

    $scope.getMediaType = function(){
        $http({
            url: '/api/1.0/mediaType/list',
            method: 'GET',
            data: $.param({
                'state' : 0
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.mediaTypes = res.result;
            }
        }).error(function(response){
            $scope.mediaTypes = {};
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
            field: 'fileName',
            displayName: '文件名称'
        },{
            field: 'suffix',
            displayName: '格式'
        },{
            field: 'type',
            displayName: '类型',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{mediaTypes[COL_FIELD]}}</span></div>'
        },{
            field: 'url',
            displayName: '外链'
        },{
            field: 'size',
            displayName: '大小(MB)',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD/1024/1024 | number:3}}</span></div>'
        },{
            field: 'remark',
            displayName: '备注'
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
    $scope.getOrganization();
    $scope.getSuffixType();
    $scope.getMediaType();
});


