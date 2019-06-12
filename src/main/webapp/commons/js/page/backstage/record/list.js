app.controller('recordListController', function($scope, $http, $stateParams){
    $scope.userTypes        = ["情感分析师","情感导师"];
    $scope.fileTypes        = ["wx备份文件","录音文件"];
    $scope.userType;
    $scope.userName;
    $scope.num;
    $scope.outGoingNum;
    $scope.sim;
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
        $scope.userName         = '';
        $scope.num           = '';
        $scope.outGoingNum       = '';
        $scope.sim = '';
        $scope.userType = '';
        $scope.fileType = '';
    };

    $scope.search = function(){
        $scope.pagingOptions.currentPage = 1;
        $scope.getData();
    };

    $scope.refresh = function(){
        $scope.getData();
    };

    $scope.edit = function(id){
        $state.go('recordEdit', {'id' : id});
    };
    
    $scope.add = function(){
        $state.go('recordAdd', {'organizationId' : $scope.organizationId});
    };

    $scope.getData = function(){
        //查询参数
        var pageNum       = $scope.pagingOptions.currentPage,
            pageSize      = $scope.pagingOptions.pageSize,
            userName       = $scope.userName,
            num       = $scope.num,
            outGoingNum       = $scope.outGoingNum,
            sim       = $scope.sim,
            userType       = $scope.userType,
            fileType       = $scope.fileType
        $http({
            url: '/api/1.0/record/page',
            method: 'POST',
            data: $.param({
                'pageNum': pageNum,
                'pageSize': pageSize,
                'userName': userName,
                'num': num,
                'outGoingNum': outGoingNum,
                'sim': sim,
                'userType': userType,
                'fileType': fileType,
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
            url: '/api/1.0/record/' + id,
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


    $scope.play = function(path, fileType){
        // alert(fileType);
        if('wx备份文件' == fileType){
            window.open(path);
        }else {
            mesg=open("cnrose","DisplayWindow","toolbar=no,,menubar=no,location=no,scrollbars=no");
            mesg.moveTo(screen.availWidth/2,screen.availHeight/2);
            mesg.resizeTo(350,120);
            mesg.document.write("<HEAD><TITLE>录音播放</TITLE></HEAD>");
            mesg.document.write("<audio src='"+path+"' controls='controls'>Your browser does not support the audio tag. </audio>");
        }

    };

    //初始化数据
    $scope.getData();

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
            field: 'userName',
            displayName: '使用者'
        },{
            field: 'userType',
            displayName: '使用者类型'
        },{
            field: 'num',
            displayName: '本机号码'
        },{
            field: 'outGoingNum',
            displayName: '呼出号码'
        },{
            field: 'createTime',
            displayName: '呼出时间',
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
        },{
            field: 'time',
            displayName: '时长(秒)'
        },{
            field: 'fileType',
            displayName: '文件类型'
        },{
            // field: 'path',
            displayName: '通话记录',
            // cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a href="/api/1.0/record/testHttpMessageDown/{{row.getProperty(\'id\')}}">下载</a></span></div>'
            // cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><audio ng-src="http://upyun.elletter.com/meiRongAsk/luyin/dhly_89860040191604470827_10086_42350_1512702362638.mp3" controls="controls">Your browser does not support the audio tag. </audio></div>'
            cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a href="javascript:;" ng-click="play(row.getProperty(\'path\'), row.getProperty(\'fileType\'))">[播放/下载]</a></span></div>'
            // },{
            //     field: 'createTime',
            //     displayName: '创建时间',
            //     cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
            // },{
            //     field: 'updateTime',
            //     displayName: '更新时间',
            //     cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text>{{COL_FIELD | date:"yyyy-MM-dd HH:mm:ss"}}</span></div>'
            // },{
            //     displayName: '操作',
            //     cellTemplate: '<div class="ngCellText" ng-class="col.colIndex()"><span ng-cell-text><a href="javascript:;" ng-click="delete(row.getProperty(\'id\'))">[删除]</a></span></div>'
        }]
    };


});