var app = angular.module('app', ['ui.router', 'oc.lazyLoad', 'ngGrid', 'ckeditor', 'ngSanitize', 'ui.bootstrap', 'monospaced.qrcode']);

app.config(['$controllerProvider', '$compileProvider', '$filterProvider', '$provide', function($controllerProvider, $compileProvider, $filterProvider, $provide){
    app.controller = $controllerProvider.register;
    app.directive  = $compileProvider.directive;
    app.filter     = $filterProvider.register;
    app.factory    = $provide.factory;
    app.service    = $provide.service;
    app.constant   = $provide.constant;
    app.value      = $provide.value;

}]).run(['$rootScope', '$state', '$stateParams', function($rootScope, $state, $stateParams){
    $rootScope.$state       = $state;
    $rootScope.$stateParams = $stateParams;

}]).config(['$stateProvider', function($stateProvider){
	$stateProvider
    //    .state('client', {
		//url: '/client',
		//templateUrl: '/page/platform/client',
		//controller: 'clientController',
		//resolve: {
		//	deps: ['$ocLazyLoad', function($ocLazyLoad){
		//		return $ocLazyLoad.load([
    //                basePath + '/commons/css/page/platform/common.css',
    //                basePath + '/commons/js/page/platform/client/index.js'
		//		]);
		//	}]
		//}
    //}).state('client.list', {
    //    url: '/client/list/:organizationId',
    //    templateUrl: function($stateParams){
    //        return '/page/platform/client/list/' + $stateParams.organizationId;
    //    },
    //    controller: 'clientListController',
    //    resolve: {
    //        deps: ['$ocLazyLoad', function($ocLazyLoad){
    //            return $ocLazyLoad.load([
    //                basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
    //                basePath + '/commons/js/plugin/ng-grid/theme.css',
    //                basePath + '/commons/css/page/platform/common.css',
    //                basePath + '/commons/js/page/platform/client/list.js'
    //            ]);
    //        }]
    //    }
    //}).state('purchaseList', {
    //        url: '/purchaseList/:accountId',
    //        templateUrl: function($stateParams){
    //            return '/page/platform/purchase/list/' + $stateParams.accountId;
    //        },
    //        controller: 'purchaseListController',
    //        params: {
    //            'accountId'      : '',
    //            'organizationId' : ''
    //        },
    //        resolve: {
    //            deps: ['$ocLazyLoad', function($ocLazyLoad){
    //                return $ocLazyLoad.load([
    //                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
    //                    basePath + '/commons/js/plugin/ng-grid/theme.css',
    //                    basePath + '/commons/css/page/platform/common.css',
    //                    basePath + '/commons/js/page/platform/client/purchaseList.js'
    //                ]);
    //            }]
    //        }
    //    }).state('purchaseAdd', {
    //    url: '/purchaseAdd/:accountId',
    //    templateUrl: function($stateParams){
    //        return '/page/platform/purchase/add/' + $stateParams.accountId;
    //    },
    //    controller: 'purchaseAddController',
    //    params: {
    //        'accountId'      : '',
    //        'organizationId' : ''
    //    },
    //    resolve: {
    //        deps: ['$ocLazyLoad', function($ocLazyLoad){
    //            return $ocLazyLoad.load([
    //                basePath + '/commons/js/plugin/ng-grid/theme.css',
    //                basePath + '/commons/css/page/platform/common.css',
    //                basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
    //                basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
    //                basePath + '/commons/js/utils.js',
    //                basePath + '/commons/js/plugin/json2/json2.js',
    //                basePath + '/commons/js/page/platform/client/purchaseAdd.js'
    //            ]);
    //        }]
    //    }
    //}).state('purchaseEdit', {
    //    url: '/purchaseEdit/:id',
    //    templateUrl: function($stateParams){
    //        return '/page/platform/purchase/edit/' + $stateParams.id;
    //    },
    //    controller: 'purchaseEditController',
    //    params: {
    //        'accountId'      : '',
    //        'organizationId' : '',
    //        'id'             : ''
    //    },
    //    resolve: {
    //        deps: ['$ocLazyLoad', function($ocLazyLoad){
    //            return $ocLazyLoad.load([
    //                basePath + '/commons/js/plugin/ng-grid/theme.css',
    //                basePath + '/commons/css/page/platform/common.css',
    //                basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
    //                basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
    //                basePath + '/commons/js/utils.js',
    //                basePath + '/commons/js/plugin/json2/json2.js',
    //                basePath + '/commons/js/page/platform/client/purchaseEdit.js'
    //            ]);
    //        }]
    //    }
    //})
    .state('purchase', {
        url: '/purchase',
        templateUrl: '/page/platform/purchase',
        controller: 'purchaseController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/page/platform/purchase/index.js'
                ]);
            }]
        }
    }).state('purchase.list', {
        url: '/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/platform/purchase/list/' + $stateParams.organizationId;
        },
        controller: 'purchaseListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/page/platform/purchase/list.js'
                ]);
            }]
        }
    }).state('purchaseAdd', {
        url: '/api/1.0/purchase/add/:organizationId',
        templateUrl: function($stateParams){
            return '/page/platform/purchase/add/' + $stateParams.organizationId;
        },
        controller: 'purchaseAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/page/platform/purchase/add.js'
                ]);
            }]
        }
    }).state('purchaseEdit', {
        url: '/api/1.0/purchase/edit/:id',
        templateUrl: function($stateParams){
            return '/page/platform/purchase/edit/' + $stateParams.id;
        },
        controller: 'purchaseEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/page/platform/purchase/edit.js'
                ]);
            }]
        }
    }).state('product', {
        url: '/product',
        templateUrl: '/page/platform/product',
        controller: 'productController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/page/platform/product/index.js'
                ]);
            }]
        }
    }).state('product.list', {
        url: '/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/platform/product/list/' + $stateParams.organizationId;
        },
        controller: 'productListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/page/platform/product/list.js'
                ]);
            }]
        }
    }).state('productAdd', {
        url: '/product/add/:organizationId',
        templateUrl: '/page/platform/product/add',
        controller: 'productAddController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/platform/product/add.js'
                ]);
            }]
        }
    }).state('productEdit', {
        url: '/product/edit/:id',
        templateUrl: function($stateParams){
            return '/page/platform/product/edit/' + $stateParams.id;
        },
        controller: 'productEditController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/css/page/backstage/common.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.css',
                    basePath + '/commons/js/plugin/angular-ui-select/select.min.js',
                    basePath + '/commons/js/utils.js',
                    basePath + '/commons/js/plugin/json2/json2.js',
                    basePath + '/commons/js/page/platform/product/edit.js'
                ]);
            }]
        }
    });
}]);