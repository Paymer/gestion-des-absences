import ngRoute from 'angular-route'

// Components
import menuEmployeComponent from "./menuEmploye.component"


const menuEmployeModule = angular
    .module('menuEmployeModule', [ngRoute])
    .component('menuEmployeComponent', menuEmployeComponent)

export default menuEmployeModule