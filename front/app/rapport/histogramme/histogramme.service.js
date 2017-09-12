export default class HistogrammeService {

constructor(apiUrls, $http, connexionService) {
        this.apiUrls = apiUrls;
        this.$http = $http;

    }
    
    //trouver la data pour le chart
    findData(departement, mois, year) {
        //can I add 3 parameters like that?
        this.data = this.$http.get(this.apiUrls.histogramme + "/" + departement +'/'+year+'/'+mois, {})
                  .then(result => {
                      return result.data;
                  })
                  
        return this.data;
    }

    
}