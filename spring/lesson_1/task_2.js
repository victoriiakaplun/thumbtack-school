'use strict';

const cities = [
    {name: "New York", country: "United States", population: 23522861},
    {name: "Shenzhen", country: "China", population: 23300000},
    {name: "Jakarta", country: "Indonesia", population: 31689592},
    {name: "Seoul", country: "South Korea", population: 25514000},
    {name: "Tokyo", country: "Japan", population: 43868229},
    {name: "Guangzhou", country: "China", population: 25000000},
    {name: "Manila", country: "Philippines", population: 29300000},
    {name: "Delhi", country: "India", population: 21753486},
    {name: "Beijing", country: "China", population: 24900000},
    {name: "Shanghai", country: "China", population: 38700000},
];

    cities
        .filter(city => city.country === 'China')
        .sort((prev, next) => (prev.population < next.population) ? 1 : -1)
        .forEach(city => console.log(city));
