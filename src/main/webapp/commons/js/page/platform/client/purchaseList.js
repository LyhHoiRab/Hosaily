app.controller('purchaseListController', function($scope, $http, $state, $stateParams, $modal){
    //查询列表
    $scope.organizationId = $stateParams.organizationId;
    $scope.accountId      = $stateParams.accountId;
    $scope.state          = '';
    $scope.purchaseState  = '';
    //下拉
    $scope.states         = {};
    $scope.purchaseStates = {};
    //列表参数
    $scope.list = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.reset = function(){
        $scope.state         = '';
        $scope.purchaseState = '';
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
            url: '/api/1.0/purchase/page',
            method: 'POST',
            data: $.param({
                'pageNum'        : $scope.pagingOptions.currentPage,
                'pageSize'       : $scope.pagingOptions.pageSize,
                'organizationId' : $scope.organizationId,
                'accountId'      : $scope.accountId,
                'state'          : $scope.state,
                'purchaseState'  : $scope.purchaseState
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

    $scope.getPurchaseState = function(){
        $http({
            url: '/api/1.0/purchaseState/list',
            method: 'GET'
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.purchaseStates = res.result;
            }
        }).error(function(response){
            $scope.purchaseStates = {};
        });
    };

    $scope.purchaseAdd = function(){
        $state.go('purchaseAdd', {'accountId':$scope.accountId, 'organizationId':$scope.organizationId});
    };

    $scope.purchaseEdit = function(id){
        $state.go('purchaseEdit', {'id':id, 'accountId':$scope.accountId, 'organizationId':$scope.organizationId});
    };

    $scope.openModal = function(id){
        var modalInstance = $modal.open({
            templateUrl: 'modal',
            controller: modalInstanceCtrl,
            resolve: {
                qrcodeString : function(){
                    return 'http://b35fe28b.ngrok.io/page/h5/goPay?purchaseId=' + id;
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
            field: 'orderTime',
            displayName: '下单时间',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            field: 'purchaseState',
            displayName: '流程状态',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{purchaseStates[COL_FIELD]}}</span></div>'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="purchaseEdit(row.getProperty(\'id\'))">[修改]</a><a ng-click="openModal(row.getProperty(\'id\'))">[支付链接二维码]</a></span></div>'
        }]
    };

    //初始化数据
    $scope.getData();
    $scope.getState();
    $scope.getPurchaseState();
});

var modalInstanceCtrl = function($scope, $modalInstance, qrcodeString){
    $scope.cancel = function(){
        $modalInstance.dismiss('cancel');
    };

    $scope.qrcodeString = qrcodeString;
};