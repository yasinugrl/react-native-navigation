export const MockNavigation = () => {
  jest.mock('./../adapters/NativeCommandsSender', () => require('./NativeCommandsSender.tsx'));
  jest.mock('./../adapters/NativeEventsReceiver', () => require('./NativeEventsReceiver.ts'));
};

export { Application } from './Application';
