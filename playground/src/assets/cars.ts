import { ImageRequireSource } from 'react-native';

export type CarItem = {
  id: string;
  name: string;
  image: ImageRequireSource;
  color: string;
  description: string;
};

const cars: CarItem[] = [
  {
    id: '1',
    name: 'Lamborghini Diablo',
    image: require('../../img/cars/diablo.jpg'),
    color: '#b00001',
    description: `The Lamborghini Diablo is a high-performance mid-engine sports car that was built by Italian automotive manufacturer Lamborghini between 1990 and 2001. It is the first production Lamborghini capable of attaining a top speed in excess of 320 kilometres per hour (200 mph). After the end of its production run in 2001, the Diablo was replaced by the Lamborghini Murciélago. The name Diablo means "devil" in Spanish.`,
  },
  {
    id: '2',
    name: 'Lamborghini Countach',
    image: require('../../img/cars/countach.jpg'),
    color: '#c7cfb7',
    description: `The Lamborghini About this soundCountach (help·info) is a rear mid-engine, rear-wheel-drive sports car produced by the Italian automobile manufacturer Lamborghini from 1974 to 1990. It is one of the many exotic designs developed by Italian design house Bertone, which pioneered and popularized the sharply angled "Italian Wedge" shape.

      The style was introduced to the public in 1970 as the Lancia Stratos Zero concept car. The first showing of the Countach prototype was at the 1971 Geneva Motor Show, as the Lamborghini LP500 concept.[6] The Countach also popularized the "cab forward" design concept, which pushes the passenger compartment forward for a more aggressive look.`,
  },
  {
    id: '3',
    name: 'Lamborghini Aventador',
    image: require('../../img/cars/aventador.jpg'),
    color: 'rgb(169,180,202)',
    description:
      'The Lamborghini Aventador (Spanish pronunciation: [aβentaˈðoɾ]) is a mid-engine sports car produced by the Italian automotive manufacturer Lamborghini. In keeping with Lamborghini tradition, the Aventador is named after a fighting bull.',
  },
  {
    id: '4',
    name: 'Lamborghini Huracán',
    image: require('../../img/cars/huracan.jpg'),
    color: '#fff',
    description: `The Lamborghini Huracán (Spanish for "hurricane"; [uɾaˈkan]) is a sports car manufactured by Italian automotive manufacturer Lamborghini replacing the previous V10 offering, the Gallardo.[4] The Huracán made its worldwide debut at the 2014 Geneva Auto Show,[5] and was released in the market in the second quarter of 2014. The LP 610-4 designation comes from the car having a 610 metric horsepower and 4 wheel drive, while LP stands for "Longitudinale Posteriore", which refers to the longitudinal mid-rear engine position.

    Engine
    The Huracán maintains the 5.2-litre naturally aspirated Audi/Lamborghini V10 engine from the Gallardo, tuned to generate a maximum power output of 449 kW (602 hp; 610 PS). To ensure its balance and performance, the car is mid-engined. The engine has both direct fuel injection and multi-point fuel injection. It combines the benefits of both of these systems; it is the first time this combination is used in a V10 engine. To increase its efficiency, the Huracán's engine also includes a start-stop system.[citation needed]

    Performance
    With a curb weight of 1,553 kg (3,424 lb), the Huracán LP610-4 has a power-to-weight ratio of 2.55 kg (5.62 lb) per horsepower.

    Road test acceleration of LP 610-4 (measurements by Top gear magazine)
    0–97 km/h (60 mph): 2.5 seconds[3]
    0–300 km/h (186 mph): 27.6 seconds[8]
    0–​1⁄4 mile: 10.4 seconds at 217 km/h (135 mph)[3]
    0–1 km (0.62 mi): 19.1 seconds at 272.20 km/h (169 mph)[9]
    Maximum speed: 341 km/h (212 mph)[10]`,
  },
  {
    id: '5',
    name: 'Lamborghini Urus',
    image: require('../../img/cars/urus.jpg'),
    color: 'rgb(207,216,231)',
    description:
      'The Lamborghini Aventador (Spanish pronunciation: [aβentaˈðoɾ]) is a mid-engine sports car produced by the Italian automotive manufacturer Lamborghini. In keeping with Lamborghini tradition, the Aventador is named after a fighting bull.',
  },
  {
    id: '6',
    name: 'Lamborghini Sián FKP 37',
    image: require('../../img/cars/sian.jpg'),
    color: 'rgb(153,149,99)',
    description: `The Lamborghini Sián FKP 37 is a mid-engine hybrid sports car produced by the Italian automotive manufacturer Lamborghini. Unveiled online on 3 September 2019, the Sián is the first hybrid production vehicle produced by the brand.
      Based on the Lamborghini Aventador, the Sián FKP 37 shares its engine with the SVJ variant of the Aventador, but an electric motor integrated into the gearbox adds another 25 kW to the power output. Other modifications to the engine include the addition of titanium intake valves, a reconfigured ECU and a new exhaust system raising the power output to 577 kW. The total power output is 602 kW, making the Sián the most powerful production Lamborghini. The engine is connected to a 7-speed automated manual transmission[4] and the car employs an electronically controlled all-wheel-drive system with a rear mechanical self-locking differential for improved handling.

      The power for the electric motor is stored in a supercapacitor unit instead of conventional lithium-ion batteries. The supercapacitor unit is integrated with the electric motor into the gearbox in order for a better weight distribution. Supercapacitors were chosen due to their ability to store three times the power of a conventional lithium-ion battery of the same capacity. The unit installed in the car is an evolution of the Aventador's starter motor and can store ten times more power than the unit it is based on. A regenerative braking system helps generate enough energy to recharge the supercapacitors. The electric motor counters the effect of deceleration and provides a power boost to the driver at speeds up to 130 km/h. The motor supports low-speed manoeuvres such as parking and reversing.

      The improvements made to the car accelerate it from 0 to 100 km/h in 2.8 seconds and attain an electronically limited top speed of 350 km/h but the official top speed is to be confirmed.

      The exterior design incorporates a wedge shape, a trademark of famed automobile designer Marcello Gandini and mixes that with the design of the Terzo Millennio concept introduced two years prior. The Y shaped daytime running headlights are inspired by the Terzo Millennio while at the rear an active fixed rear wing with the number "63" embossed on its winglets to honour the company's year of incorporation creates downforce. Downforce is maximised by the model's prominent side air intakes and large carbon-fibre front splitter. A transparent "Peroscopio" glass panel runs from the centre of the roof and rolls back into the slatted engine cover adds light and visibility for the occupants, and the six hexagonal taillights are an inspiration from the Countach.

      Along with the wing, active cooling vanes at the rear are used which are activated by a smart material that reacts to heat. When a certain temperature is reached, the vanes rotate for extra airflow.

      The interior is based heavily on the Aventador's interior, but the centre console has been tidied up and a portrait touchscreen first seen in the Huracán Evo is one of the key differences. The leather upholstery has been done by Poltrona Frau, an Italian furniture company and 3D printed parts are used on the interior for the first time.`,
  },
];

export default cars;
