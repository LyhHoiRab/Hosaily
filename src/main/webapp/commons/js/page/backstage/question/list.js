app.controller('questionListController', function($scope, $http, $stateParams){
    // $scope.userTypes        = ["情感分析师","情感导师"];
    // $scope.userType;
    $scope.oprojects = [];
    $scope.organizationId  = $stateParams.organizationId;
    $scope.list = [];
    $scope.organizations = [];
    $scope.selected = [];
    $scope.total = 0;
    $scope.pagingOptions = {
        pageSizes: [20, 50, 100, 200],
        pageSize: 20,
        currentPage: 1
    };

    $scope.reset = function(){
        // $scope.num           = '';
        // $scope.userType = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.edit = function(id){
        $state.go('questionEdit', {'id' : id});  
    };

    $scope.add = function(){
        $state.go('questionAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.getData = function(){
        //查询参数
        var pageNum       = $scope.pagingOptions.currentPage,
            pageSize      = $scope.pagingOptions.pageSize,
            num     = $scope.num,
            title   = $scope.title,
            projectId   = $scope.projectId;
        $http({
            url: '/api/1.0/question/page',
            method: 'POST',
            data: $.param({
                'pageNum': pageNum,
                'pageSize': pageSize,
                'num':num,
                'title':title,
                'projectId':projectId,
                'organizationId' : $scope.organizationId
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

    $scope.delete = function(id){
        $http({
            url: '/api/1.0/question/' + id,
            method: 'DELETE'
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.msg);
                $scope.pagingOptions.currentPage = 1;
                $scope.getData();
            }else{
                alert(res.msg);
            }
        }).error(function(response){

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
            field: 'title',
            displayName: '题目内容'
        },{
            field: 'project.title',
            displayName: '测试项目名称'
        },{
            field: 'num',
            displayName: '题目编号'
        },{
            field: 'isResult',
            displayName: '是否是最终答案'
        },{
            field: 'preQuestionId',
            displayName: '上一题问题'
        },{
            //     field: 'createTime',
            //     displayName: '创建时间',
            //     cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
            // },{
            //     field: 'updateTime',
            //     displayName: '更新时间',
            //     cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
            // },{
            displayName: '操作',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="questionEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a><a href="javascript:;" ng-click="delete(row.getProperty(\'id\'))">[删除]</a></span></div>'
        }]
    };

    $scope.getProject = function(){
        $http({
            url: '/api/1.0/project/list',
            method: 'POST',
            data: $.param({
                'state' : 0,
                'organizationId' : $scope.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.projects = res.result;
            }
        }).error(function(response){
            $scope.projects = [];
        });
    };

    $scope.getProject();

    //初始化数据
    $scope.getData();
});