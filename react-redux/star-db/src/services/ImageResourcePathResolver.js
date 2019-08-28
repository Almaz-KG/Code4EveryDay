export default class ImageResourcePathResolver {

    _apiBase = 'https://starwars-visualguide.com/assets/img/';

    getPlanetImage(id) {
        return `${this._apiBase}planets/${id}.jpg`
    }
}