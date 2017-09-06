export default class ModifAbsenceService{

constructor(apiUrls,$resource){
    this.url = apiUrls.demandeAbsence;
    this.demandeAbsenceRessource = $resource(this.url,{
        save: { method: "PUT" }
    })
}

confirmeEnvoiAbsence(absence){
    this.modifAbsenceRessource.save(absence);
}


}