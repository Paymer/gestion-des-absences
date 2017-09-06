const frontUrls = {

    connexion:  '/connexion',
    accueil: '/',
    //afichage liste absence
    absence: '/absence',
   
    
    creerDemande: this.absence + '/demande',//creer demande
    modifDemande: this.absence + '/modification',//modification Demande
    planningDemande: this.absence + '/planning',//calendar 
    validationDemande: this.absence + '/validation',//validation demande

    //rapports
    rapport: '/rapport',
    histogramme: this.rapport + '/histogramme',
    vdjc: this.rapport + '/vdjc',

    //jours feries
    ferie:  '/ferie',
    creerFerie: this.ferie + '/creation',
    modifFerie: this.ferie + '/modification',


}

export default frontUrls