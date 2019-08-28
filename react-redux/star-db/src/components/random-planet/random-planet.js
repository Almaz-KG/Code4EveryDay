import React, {Component} from 'react';

import './random-planet.css';
import SwapiService from "../../services/SwapiService";
import ImageResourcePathResolver from "../../services/ImageResourcePathResolver";

export default class RandomPlanet extends Component {

    constructor() {
        super();
        this.swapiService = new SwapiService();
        this.imageResourcePathResolver = new ImageResourcePathResolver();
        this.updatePlanet();
    }

    state = {
        planet: {}
    };

    onPlanetLoaded = (planet) => {
        this.setState({planet})
    };

    updatePlanet() {
        const id = Math.floor(Math.random() * 25 + 1);
        this.swapiService.getPlanet(id)
            .then(this.onPlanetLoaded);
    }

    render() {
        const {planet: {id, name, population, rotationPeriod, diameter}} = this.state;

        return (
            <div className="random-planet jumbotron rounded">
                <img className="planet-image"
                     src={this.imageResourcePathResolver.getPlanetImage(id)}/>
                <div>
                    <h4>Planet {name}</h4>
                    <ul className="list-group list-group-flush">
                        <li className="list-group-item">
                            <span className="term">Population</span>
                            <span>{population}</span>
                        </li>
                        <li className="list-group-item">
                            <span className="term">Rotation Period</span>
                            <span>{rotationPeriod}</span>
                        </li>
                        <li className="list-group-item">
                            <span className="term">Diameter</span>
                            <span>{diameter}</span>
                        </li>
                    </ul>
                </div>
            </div>

        );
    }
}