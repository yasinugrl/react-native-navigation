
export const MockNavigation = () => {
    jest.mock('./../adapters/NativeCommandsSender', () => require('./NativeCommandsSender.tsx'));
    jest.mock('./../adapters/NativeEventsReceiver', () => require('./NativeEventsReceiver.ts'));
    return require('./Application').Application;
};

