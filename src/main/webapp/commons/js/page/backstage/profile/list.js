app.controller('profileListController', function ($scope, $http, $state, $stateParams) {
    // $scope.userTypes        = ["情感分析师","情感导师"];
    // $scope.userType;
    $scope.organizationId = $stateParams.organizationId;
    $scope.list = [];
    $scope.organizations = [];
    $scope.states = {};
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.reset = function () {
        // $scope.num           = '';
        // $scope.userType = '';
        $scope.state = '';
    };

    $scope.search = function () {
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function () {
        $scope.getData();
    };

    $scope.edit = function (id) {
        $state.go('profileEdit', {'id': id});
    };


    $scope.add = function () {
        $state.go('profileAdd', {'organizationId': $scope.organizationId});
    };

    $scope.getData = function () {
        //查询参数
        var pageNum = $scope.pagingOptions.currentPage,
            pageSize = $scope.pagingOptions.pageSize,
            num = $scope.num,
            title = $scope.title;
        $http({
            url: '/api/1.0/app/profile/page',
            method: 'POST',
            data: $.param({
                'pageNum': pageNum,
                'pageSize': pageSize,
                'num': num,
                'title': title,
                'state': $scope.state,
                'organizationId': $scope.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function (res, status, headers, config) {
            if (res.success) {
                $scope.list = res.result.content;
                $scope.total = res.result.total;
            }
        }).error(function (response) {
            $scope.list = [];
            $scope.total = 0;
            console.error(response);
        });
    };


    $scope.getState = function () {
        $http({
            url: '/api/1.0/usingState/list',
            method: 'GET'
        }).success(function (res, status, headers, config) {
            if (res.success) {
                $scope.states = res.result;
            }
        }).error(function (response) {
            $scope.states = [];
        });
    };

    $scope.delete = function (id) {
        $http({
            url: '/api/1.0/app/profile/' + id,
            method: 'DELETE'
        }).success(function (res, status, headers, config) {
            if (res.success) {
                alert(res.msg);
                $scope.pagingOptions.currentPage = 1;
                $scope.getData();
            } else {
                alert(res.msg);
            }
        }).error(function (response) {

        });
    };

    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && (newVal.currentPage !== oldVal.currentPage || newVal.pageSize !== oldVal.pageSize)) {
            $scope.getData();
        }
    }, true);

    $scope.gridOptions = {
        data: 'list',
        enablePaging: true,
        enableSorting: false,
        enableRowSelection: false,
        showFooter: true,
        showSelectionCheckbox: false,
        selectWithCheckboxOnly: true,
        multiSelect: false,
        keepLastSelected: false,
        selectItems: $scope.selected,
        pagingOptions: $scope.pagingOptions,
        totalServerItems: 'total',
        i18n: 'zh-cn',
        rowHeight: 50,
        columnDefs: [{
            field: 'headImgUrl',
            displayName: '头像',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><img src="{{COL_FIELD}}" width="50" height="50" style="vertical-align:middle;"></div>'
        }, {
            field: 'name',
            displayName: '名称'
        }, {
            field: 'mobile',
            displayName: '手机号'
        }, {
            field: 'nickname',
            displayName: '昵称'
        }, {
            field: 'role',
            displayName: '角色'
        }, {
            displayName: '操作',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ng-click="edit(row.getProperty(\'id\'))">[修改]</a></span></div>'
        }]
    };
    //初始化数据
    $scope.getData();
    $scope.getState();
});