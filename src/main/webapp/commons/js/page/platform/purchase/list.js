app.controller('purchaseListController', function($scope, $http, $state, $stateParams, $modal){
    //查询列表
    $scope.organizationId = $stateParams.organizationId;
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

    $scope.openModal = function(id){
        var modalInstance = $modal.open({
            templateUrl: 'modal',
            controller: modalInstanceCtrl,
            resolve: {
                qrcodeString : function(){
                    //return 'http://ell.ishsls.com/page/h5/goPay?purchaseId=' + id;
                    return 'http://' + location.host +'/page/h5/goPay?purchaseId=' + id;
                }
            }
        });
    };

    $scope.edit = function(id){
        $state.go('purchaseEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('purchaseAdd', {'organizationId' : $scope.organizationId});
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
            }else{
                alert(res.msg);
            }
        }).error(function(response){
            console.error(response);

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
            field: 'agreement.client',
            displayName: '客户名称'
        },{
            field: 'agreement.phone',
            displayName: '客户联系电话'
        },{
            field: 'agreement.wechat',
            displayName: '客户微信'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a><a ng-click="openModal(row.getProperty(\'id\'))">[支付链接二维码]</a></span></div>'
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