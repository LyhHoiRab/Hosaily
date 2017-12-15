var app = angular.module('app', ['ui.router', 'oc.lazyLoad', 'ngGrid', 'ckeditor', 'ngSanitize', 'ui.bootstrap']);

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
	$stateProvider.state('client', {
		url: '/client',
		templateUrl: '/page/platform/client',
		controller: 'clientController',
		resolve: {
			deps: ['$ocLazyLoad', function($ocLazyLoad){
				return $ocLazyLoad.load([
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/page/platform/client/index.js'
				]);
			}]
		}
	}).state('client.list', {
        url: '/list/:organizationId',
        templateUrl: function($stateParams){
            return '/page/platform/client/list/' + $stateParams.organizationId;
        },
        controller: 'clientListController',
        resolve: {
            deps: ['$ocLazyLoad', function($ocLazyLoad){
                return $ocLazyLoad.load([
                    basePath + '/commons/js/plugin/ng-grid/ng-grid.min.css',
                    basePath + '/commons/js/plugin/ng-grid/theme.css',
                    basePath + '/commons/css/page/platform/common.css',
                    basePath + '/commons/js/page/platform/client/list.js'
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
    });
}]);