app.controller('appointmentListController', function($scope, $http, $state, $stateParams){
    //查询列表
    $scope.state           = '';
    $scope.organizationId  = $stateParams.organizationId;
    $scope.name            = '';
    $scope.phont           = '';
    $scope.type            = '';
    $scope.descirption     = '';
    $scope.minTime         = '';
    $scope.maxTime         = '';

    //下拉
    $scope.states = {};
    $scope.sexs   = {};

    //列表参数
    $scope.list = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [10, 50, 100, 200],
        pageSize: 10,
        currentPage: 1
    };

    $scope.edit = function(id){
        $state.go('appointmentEdit', {id : id});
    };

    $scope.reset = function(){
        $scope.state          = '';
        $scope.name           = '';
        $scope.phone          = '';
        $scope.type           = '';
        $scope.description    = '';
        $scope.minTime        = '';
        $scope.maxTime        = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.print = function(){
        $http({
            url: '/api/1.0/appointment/export',
            method: 'POST',
            data: $.param({
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'phone'          : $scope.phone,
                'name'           : $scope.name,
                'state'          : $scope.state,
                'organizationId' : $scope.organizationId,
                'type'           : $scope.type,
                'description'    : $scope.description,
                'minTime'        : $scope.minTime ? moment($scope.minTime).format('YYYY-MM-DD hh:mm:ss') : $scope.minTime,
                'maxTime'        : $scope.maxTime ? moment($scope.maxTime).format('YYYY-MM-DD hh:mm:ss') : $scope.maxTime
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            responseType: 'arraybuffer'
        }).success(function(data){
            console.log(data);

            var blob = new Blob([data], {type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"});
            var objectUrl = URL.createObjectURL(blob);
            var a = document.createElement('a');
            document.body.appendChild(a);
            a.setAttribute('style', 'display:none');
            a.setAttribute('href', objectUrl);
            a.click();
            URL.revokeObjectURL(objectUrl);
        }).error(function(response){
            console.error(response);
        });
    };

    $scope.getData = function(){
        $http({
            url: '/api/1.0/appointment/page',
            method: 'POST',
            data: $.param({
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'phone'          : $scope.phone,
                'name'           : $scope.name,
                'state'          : $scope.state,
                'organizationId' : $scope.organizationId,
                'type'           : $scope.type,
                'description'    : $scope.description,
                'minTime'        : $scope.minTime ? moment($scope.minTime).format('YYYY-MM-DD hh:mm:ss') : $scope.minTime,
                'maxTime'        : $scope.maxTime ? moment($scope.maxTime).format('YYYY-MM-DD hh:mm:ss') : $scope.maxTime
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
            url: '/api/1.0/appointmentState/list',
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
        columnDefs: [{
            field: 'id',
            visible: false
        },{
            field: 'name',
            displayName: '名称'
        },{
            field: 'wechat',
            displayName: '微信号'
        },{
            field: 'phone',
            displayName: '联系方式'
        },{
            field: 'sex',
            displayName: '性别',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{sexs[COL_FIELD]}}</span></div>'
        },{
            field: 'type',
            displayName: '类型'
        },{
            field: 'description',
            displayName: '说明'
        },{
            field: 'url',
            displayName: '来源',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a href="{{COL_FIELD}}" target="_blank">{{COL_FIELD}}</a></span></div>'
        },{
            field: 'state',
            displayName: '状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{states[COL_FIELD]}}</span></div>'
        },{
            field: 'createTime',
            displayName: '预约时间',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            displayName: '操作',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[处理]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getState();
    $scope.getSex();
});