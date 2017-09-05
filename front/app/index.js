import angular from 'angular';
import RouteModule from 'angular-route';
import 'bootstrap/dist/css/bootstrap.css';
import { route } from './app.route';
import { AccueilComponent } from './accueil/accueil.component';


//Modules
import menuEmployeModule from './menuEmploye/menuEmploye.module';
import menuAdminModule from './menuAdministrateur/menuAdministrateur.module';


angular.module('app', [RouteModule, menuEmployeModule.name, menuAdminModule.name])
    .value('apiUrl', API_URL)
    .component('accueil', AccueilComponent)
    .config(route);

