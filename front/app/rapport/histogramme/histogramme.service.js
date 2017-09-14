export default class HistogrammeService {

constructor(apiUrls, $http, connexionService) {
        this.apiUrls = apiUrls;
        this.$http = $http;

    }
    
    //trouver la data pour le chart
    findData(departement, month, year) {
   
        return this.$http.get(encodeURI(this.apiUrls.histogramme + "/" + departement.split('/').join("%2F") +'/'+year+'/'+month), {})
                  .then(result => {
                      return result.data;
                  })
    }

    

    getDepartements() {
        return this.$http.get(this.apiUrls.departement, {})
             .then(result => result.data)
             
    }
    
}