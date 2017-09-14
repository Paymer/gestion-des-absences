export default class creationFerieService{

constructor(apiUrls, $resource){
    this.url = apiUrls.ferie
    this.creationFerieRessource = $resource(this.url,{
        save: { method: "POST" }
    })
}

confirmeEnvoiFerie(ferie){
	return this.creationFerieRessource.save(ferie).$promise;
}


}