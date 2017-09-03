import './accueil.component.css';
import template from './accueil.component.html';

class controller {
    constructor () {
        this.titre = 'Accueil';
    }
}

export let AccueilComponent = {
    template,
    controller,
    bindings: {}
};
