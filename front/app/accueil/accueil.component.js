import './accueil.component.css';
import template from './accueil.component.html';

class controller {
    constructor () {
        this.titre = "Bienvenue sur l'application GDA - Gestion Des Absences";
    }
}

export let AccueilComponent = {
    template,
    controller,
    bindings: {}
};
