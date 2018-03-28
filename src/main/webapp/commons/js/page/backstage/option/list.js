app.controller('optionListController', function($scope, $http, $stateParams){
    // $scope.userTypes        = ["情感分析师","情感导师"];
    // $scope.userType;

    $scope.options        = ["A","B","C","D","E"];
    $scope.organizations = [];
    $scope.projects = [];
    $scope.questions = [];

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
        // $scope.title = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.edit = function(id){
        $state.go('optionEdit', {'id' : id});
    };

    $scope.add = function(){
        $state.go('optionAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.getData = function(){
        //查询参数
        var pageNum       = $scope.pagingOptions.currentPage,
            pageSize      = $scope.pagingOptions.pageSize,
            title   = $scope.title,
            projectId   = $scope.projectId,
            questionId   = $scope.questionId,
            questionOption   = $scope.questionOption;
        $http({
            url: '/api/1.0/option/page',
            method: 'POST',
            data: $.param({
                'pageNum': pageNum,
                'pageSize': pageSize,
                'title':title,
                'projectId':projectId,
                'questionId':questionId,
                'questionOption':questionOption,
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
            url: '/api/1.0/option/' + id,
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
            displayName: '选项内容'
        },{
            field: 'project.title',
            displayName: '测试项目'
        },{
            field: 'question.title',
            displayName: '问题'
        },{
            field: 'nestQuestion.title',
            displayName: '下一道问题'
        },{
            field: 'questionOption',
            displayName: '选项编号'
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
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a ui-sref="optionEdit({id:\'{{row.getProperty(\'id\')}}\'})">[修改]</a><a href="javascript:;" ng-click="delete(row.getProperty(\'id\'))">[删除]</a></span></div>'
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


    $scope.selectQuestion = function(){
        // alert($scope.option.project.id);
        $http({
            url: '/api/1.0/question/list',
            method: 'POST',
            data: $.param({
                'state' : 0,
                'projectId' : $scope.projectId,
                'organizationId' : $scope.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                alert(res.result);
                $scope.questions = res.result;
            }
        }).error(function(response){
            $scope.questions = [];
        });

    }

    $scope.getQuestion = function(){
        // alert($scope.option.project);
        $http({
            url: '/api/1.0/question/list',
            method: 'POST',
            data: $.param({
                'state' : 0,
                'projectId' : $scope.projectId,
                'organizationId' : $scope.organizationId
            }),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }).success(function(res, status, headers, config){
            if(res.success){
                $scope.questions = res.result;
            }
        }).error(function(response){
            $scope.questions = [];
        });
    };

    $scope.getProject();
    // $scope.getQuestion();

    //初始化数据
    $scope.getData();
});