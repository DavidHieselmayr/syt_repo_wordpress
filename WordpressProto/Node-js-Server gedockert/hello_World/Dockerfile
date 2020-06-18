FROM node:current-slim
WORKDIR /usr/src/nodeFun/
COPY ./package.json .
RUN npm install
EXPOSE 4540
COPY . .
CMD ["node","app.js"]



