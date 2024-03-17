let cachedb:any = {};

function addSingleElement(name: string, key: any, value: any): void {
    if(cachedb[name] === undefined) {
        cachedb[name] = {}
   } 

   cachedb[name][value[key]] = value;
}

function addAllElements(name: string, key: any, values: Array<any>) {
    if(cachedb[name] === undefined) {
        cachedb[name] = {}
    }

    for(let i = 0; i < values.length; i++) {
        cachedb[name][values[i][key]] = values[i];
    }
}

export default {
    cache: cachedb,
    addSingleElement: addSingleElement,
    addAllElements: addAllElements
};