import ngRoute from 'angular-route'

// Components
import menuAdminComponent from "./menuAdministrateur.component"


const menuAdminModule = angular
    .module('menuEmployeModule', [ngRoute])
    .component('menuAdminComponent', menuAdminComponent)

export default menuAdminModule