export default class SwapiService {

    _apiBase = 'https://swapi.co/api/';

    async getAllPersons(){
        const result = await this.getResource("people/");

        return result.results;
    }

    async getPerson(id){
        return this.getResource(`people/${id}`);
    }

    async getAllPlanets(){
        const result = await this.getResource("planets/");

        return result.results.map(this._transformPlanet);
    }

    async getPlanet(id){
        const planet = await this.getResource(`planets/${id}`);
        return this._transformPlanet(planet);
    }

    async getAllStarships(){
        const result = await this.getResource("starships/");

        return result.results;
    }

    async getStarship(id){
        return this.getResource(`starships/${id}`)
    }

    async getResource(url){
        const res = await fetch(`${this._apiBase}${url}`);

        if (!res.ok){
            throw new Error (`Could not fetch ${url}, received code: ${res.status}`)
        }

        return await res.json();
    }

    _extractId(url){
        const idRegexp = /\/([0-9]*)\/$/;
        return url.match(idRegexp)[1];
    }

    _transformPlanet(planet){
        return {
            id: this._extractId(planet.url),
            name: planet.name,
            population: planet.population,
            rotationPeriod: planet.rotationPeriod,
            diameter: planet.diameter
        }
    }
}